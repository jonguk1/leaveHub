package com.example.leaveHub.service.leave;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.leaveHub.mapper.LeaveRequestMapper;
import com.example.leaveHub.service.file.FileService;
import com.example.leaveHub.vo.LeaveRequestVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeaveRequestServiceImpl implements LeaveRequestService {

    private final LeaveRequestMapper leaveRequestMapper;

    private final FileService fileService;

    // 연차 신청 등록
    @Override
    @Transactional
    public void insertLeaveRequest(LeaveRequestVO leaveRequest, MultipartFile uploadFile) {
        String type = leaveRequest.getLeaveType();
        boolean isFileRequired = "병가".equals(type) || "경조사".equals(type);

        if (isFileRequired) {
            // 병가인데 파일이 없는경우
            if (uploadFile == null || uploadFile.isEmpty()) {
                throw new RuntimeException(type + " 신청 시 증빙 서류는 필수입니다.");
            }

            // 파일 저장 진행
            String storedFileName = fileService.saveFile(uploadFile);
            leaveRequest.setStoredFileName(storedFileName);
            leaveRequest.setOriginFileName(uploadFile.getOriginalFilename());
            leaveRequest.setFilePath(fileService.getUploadDir());
        }

        int result = leaveRequestMapper.insertLeaveRequest(leaveRequest);
        if (result == 0) {
            throw new RuntimeException("연차 신청 등록에 실패했습니다.");
        }
    }

    // 내 연차 갯수 조회
    @Override
    public int getLeaveRequestCount(String userId) {
        return leaveRequestMapper.getLeaveRequestCount(userId);
    }

    // 내 연차 조회
    @Override
    public List<LeaveRequestVO> getLeaveRequestsByUserId(Map<String, Object> params) {
        return leaveRequestMapper.getLeaveRequestsByUserId(params);
    }

    // 내 연차 수정
    @Override
    @Transactional
    public void updateLeaveRequest(LeaveRequestVO vo, String userId) {
        int result = leaveRequestMapper.updateLeaveRequest(vo, userId);
        if (result == 0) {
            throw new RuntimeException("수정 권한이 없거나, 이미 처리된 신청 건입니다.");
        }
    }

    // 내 연차 삭제
    @Override
    @Transactional
    public void deleteLeaveRequest(Long leaveId) {
        int result = leaveRequestMapper.deleteLeaveRequest(leaveId);
        if (result == 0) {
            throw new RuntimeException("연차 삭제에 실패했습니다.");
        }
    }

}
