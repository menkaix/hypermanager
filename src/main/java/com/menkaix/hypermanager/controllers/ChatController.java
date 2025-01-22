package com.menkaix.hypermanager.controllers;


import com.menkaix.hypermanager.models.LLMResponseDTO;
import com.menkaix.hypermanager.models.PromptQueryDTO;
import com.menkaix.hypermanager.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/chat")
@RestController
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/discussion")
    public ResponseEntity<LLMResponseDTO> discussion(@RequestBody PromptQueryDTO promptQueryDTO) {

        LLMResponseDTO ans = chatService.discussion(promptQueryDTO);

        if(ans == null) {

            LLMResponseDTO errAns = new LLMResponseDTO() ;
            errAns.setError("Sorry, I am not able to understand your query. Please try again.");
            return ResponseEntity.badRequest().body(errAns);

        }

        return ResponseEntity.ok(ans);
    }




}
