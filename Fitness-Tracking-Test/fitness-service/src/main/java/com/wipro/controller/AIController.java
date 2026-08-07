package com.wipro.controller;

import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.wipro.service.GeminiService;
import lombok.RequiredArgsConstructor;
import com.wipro.dto.AIChatRequest;
import com.wipro.dto.UserProfileResponse;
import com.wipro.service.UserServiceClient;



@RestController
@RequiredArgsConstructor
public class AIController {

	
	private final GeminiService geminiService;
	private final UserServiceClient userServiceClient;
	
	@PostMapping("/ai/ask")
	public Map<String, String> ask(@RequestBody AIChatRequest request) {

		System.out.println("QUESTION RECEIVED = " + request.getQuestion());
		
		UserProfileResponse profile = userServiceClient.getProfile(request.getUserId());
		
		String prompt = String.format(
			    """
			    You are a professional fitness coach.

			    User Profile:
			    Age: %d
			    Height: %.1f cm
			    Weight: %.1f kg
			    Gender: %s
			    Goal: %s

			    User Question:
			    %s

			    Give a personalized fitness response based on the user's profile and goal.
			    """,
			    profile.getAge(),
			    profile.getHeight(),
			    profile.getWeight(),
			    profile.getGender(),
			    profile.getGoal(),
			    request.getQuestion()
			);

			String answer = geminiService.generateWorkout(prompt);

	    return Map.of("answer", answer);
	}
}