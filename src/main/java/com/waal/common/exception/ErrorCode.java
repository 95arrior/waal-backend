package com.waal.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "잘못된 입력입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "리소스를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다."),

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),

    // Kindergarten
    KINDERGARTEN_NOT_FOUND(HttpStatus.NOT_FOUND, "유치원을 찾을 수 없습니다."),
    KINDERGARTEN_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "유치원 멤버를 찾을 수 없습니다."),
    ALREADY_KINDERGARTEN_MEMBER(HttpStatus.CONFLICT, "이미 해당 유치원의 멤버입니다."),

    // Dog
    DOG_NOT_FOUND(HttpStatus.NOT_FOUND, "반려견을 찾을 수 없습니다."),
    DOG_NOT_OWNED(HttpStatus.FORBIDDEN, "본인의 반려견이 아닙니다."),

    // Reservation
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "예약을 찾을 수 없습니다."),
    RESERVATION_CAPACITY_EXCEEDED(HttpStatus.CONFLICT, "정원이 초과되었습니다."),
    RESERVATION_ALREADY_EXISTS(HttpStatus.CONFLICT, "해당 날짜에 이미 예약이 있습니다."),
    INVALID_RESERVATION_STATUS(HttpStatus.BAD_REQUEST, "올바르지 않은 예약 상태 변경입니다."),

    // Guardian Connection
    GUARDIAN_CONNECTION_NOT_FOUND(HttpStatus.NOT_FOUND, "보호자 연결 정보를 찾을 수 없습니다."),
    ALREADY_CONNECTED(HttpStatus.CONFLICT, "이미 연결되어 있습니다."),

    // Daily Report
    DAILY_REPORT_NOT_FOUND(HttpStatus.NOT_FOUND, "일일 보고서를 찾을 수 없습니다."),
    REPORT_ALREADY_EXISTS(HttpStatus.CONFLICT, "해당 날짜에 이미 보고서가 작성되었습니다."),

    // Invite Token
    INVITE_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "초대 토큰을 찾을 수 없습니다."),
    INVITE_TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "만료된 초대 토큰입니다."),
    INVITE_TOKEN_ALREADY_USED(HttpStatus.BAD_REQUEST, "이미 사용된 초대 토큰입니다."),

    // S3
    S3_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다."),
    INVALID_FILE_TYPE(HttpStatus.BAD_REQUEST, "지원하지 않는 파일 형식입니다.");

    private final HttpStatus status;
    private final String message;
}
