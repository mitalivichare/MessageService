package com.stackroute.activitystream;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.activitystream.dao.MessageDAO;
import com.stackroute.activitystream.model.Message;


@RestController
public class MessageController {
	
	@Autowired
	private MessageDAO messageDAO;
	
	@GetMapping("/")
	public String TestingMessage()
	{
		return "Testing in Message Controller.."; 
	}

	@PostMapping("/sendMessageToCircle")
	public ResponseEntity<?> sendMessageToCircle(@RequestBody Message message) {
		if (messageDAO.sendMessageToCircle(message))
			return new ResponseEntity<>(HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	
	@PostMapping("/sendMessageToUser")
	public ResponseEntity<?> sendMessageToUser(@RequestBody Message message) {
		if (messageDAO.sendMessageToUser(message))
			return new ResponseEntity<>(HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping("/getMessageByUser/{emailid}")
	public ResponseEntity<?> getMessageByUser(@PathVariable String emailid) {
		List<Message> allMessages;
		System.out.println(emailid);
		try {
			allMessages = messageDAO.getMessagesForUser(emailid);
			return new ResponseEntity<>(allMessages,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@RequestMapping("/getMessageByCircle/{circleId}")
	public ResponseEntity<?> getMessageByCircle(@PathVariable int circleId) {
		List<Message> allMessages;
		try {
			allMessages = messageDAO.getMessagesForCircle(circleId);
			return new ResponseEntity<>(allMessages,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

	}

}
