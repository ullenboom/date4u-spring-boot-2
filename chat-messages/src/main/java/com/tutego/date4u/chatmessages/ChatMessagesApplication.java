package com.tutego.date4u.chatmessages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ChatMessagesApplication implements CommandLineRunner {

	@Autowired MessageService messageService;

	public static void main(String[] args) {
		SpringApplication.run(ChatMessagesApplication.class, args);
	}

	@Override public void run( String... args ) throws Exception {

//		List<Message> newMessages = Arrays.asList(
//				new Message( "1+2", Arrays.asList( 1L, 2L ),
//							 "Darf ich dir einen Zeitreisewitz erzählen?",
//							 OffsetDateTime.now().minusSeconds( 20 ) ),
//				new Message( "1+2", Arrays.asList( 2L, 1L ),
//							 "Ja, gerne", OffsetDateTime.now().minusSeconds( 10 ) ),
//				new Message( "1+2", Arrays.asList( 1L, 2L ),
//							 "Du mochtest ihn nicht.", OffsetDateTime.now() ),
//				new Message( "2+3", Arrays.asList( 2L, 3L ),
//							 "Du, ich kenn 'nen Witz.", OffsetDateTime.now() )
//		);
//
//		messageService.saveAll( newMessages );
//
//		messageService.findAll().forEach( System.out::println );

//		messageService.messagesBetween(1L, 2L).forEach( System.out::println );

		messageService.search( "dir Zeitreisewitz" ).forEach( System.out::println );
	}
}
