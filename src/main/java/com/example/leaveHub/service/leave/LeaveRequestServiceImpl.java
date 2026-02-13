package com.example.leaveHub.service.leave;

import java.io.File;
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
    public void updateLeaveRequest(LeaveRequestVO vo, MultipartFile uploadFile, String userId) {
        LeaveRequestVO oldData = leaveRequestMapper.getLeaveRequestById(vo.getLeaveId());

        // 새로운 파일이 업로드된 경우
        if (uploadFile != null && !uploadFile.isEmpty()) {
            // 기존 파일이 있으면 물리적 삭제
            if (oldData != null && oldData.getStoredFileName() != null) {
                File oldFile = new File(oldData.getFilePath(), oldData.getStoredFileName());
                if (oldFile.exists())
                    oldFile.delete();
            }

            // 새로운 파일 저장
            String storedFileName = fileService.saveFile(uploadFile);
            vo.setStoredFileName(storedFileName);
            vo.setOriginFileName(uploadFile.getOriginalFilename());
            vo.setFilePath(fileService.getUploadDir());

        } else {
            // 새로운 파일이 없는 경우
            // 일반 연차로 변경한 경우
            if (!"병가".equals(vo.getLeaveType()) && !"경조사".equals(vo.getLeaveType())) {
                // 기존 파일 삭제
                if (oldData != null && oldData.getStoredFileName() != null) {
                    File oldFile = new File(oldData.getFilePath(), oldData.getStoredFileName());
                    if (oldFile.exists())
                        oldFile.delete();
                }
                vo.setStoredFileName(null);
                vo.setOriginFileName(null);
                vo.setFilePath(null);
            } else {
                // 내용만 수정한 경우, 기존 파일 정보 유지
                vo.setStoredFileName(oldData.getStoredFileName());
                vo.setOriginFileName(oldData.getOriginFileName());
                vo.setFilePath(oldData.getFilePath());
            }
        }

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
