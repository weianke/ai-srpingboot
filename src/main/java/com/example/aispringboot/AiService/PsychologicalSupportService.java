package com.example.aispringboot.AiService;

import com.example.aispringboot.dto.command.ConsultationSessionCreateDTO;
import com.example.aispringboot.entity.ConsultationSession;
import com.example.aispringboot.service.ConsultationMessageService;
import com.example.aispringboot.service.ConsultationSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PsychologicalSupportService {
    @Autowired
    private ConsultationSessionService consultationSessionService;


    @Autowired
    private ConsultationMessageService consultationMessageService;

    public StructOutPut.StreamChatSession startSession(Long userId, ConsultationSessionCreateDTO createDTO) {
        // 创建数据库会话记录
        ConsultationSession consultationSession = consultationSessionService.createSession(userId, createDTO);

        // 将初始用户消息保存到message表
        consultationMessageService.saveUserMessage(
                consultationSession.getId(),
                createDTO.getInitialMessage(),
                null);

        // 数据返回
        String sessionId = "session_" + consultationSession.getId();
       return new StructOutPut.StreamChatSession(
                sessionId,
                userId,
                createDTO.getInitialMessage(),
                System.currentTimeMillis(),
                System.currentTimeMillis() + 86400000L, // 24小时
                1,
                "ACTIVE"
        );
    }
}
