<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
    <div id="rejectModal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title">연차 신청 반려</h3>
                <p class="modal-description">반려 사유를 입력해주세요</p>
            </div>
            <div id="rejectRequestInfo" class="info-box"></div>
            <form id="rejectForm" action="/admin/reject" method="POST">
                <input type="hidden" id="leaveId" name="id">
                
                <div class="form-group">
                    <label for="rejectReason">반려 사유</label>
                    <textarea id="rejectReason" name="rejectReason" rows="4" placeholder="반려 사유를 입력하세요" required></textarea>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-outline" onclick="closeRejectModal()">취소</button>
                    <button type="submit" class="btn btn-danger">반려 확정</button>
                </div>
            </form>
        </div>
    </div>