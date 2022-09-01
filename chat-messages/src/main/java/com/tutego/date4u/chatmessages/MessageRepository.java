package com.tutego.date4u.chatmessages;

import org.springframework.data.elasticsearch.repository.*;

import java.util.List;

public interface MessageRepository
    extends ElasticsearchRepository<Message, String> {

  List<Message> findMessageByConversationIdOrderBySent(
      String conversationId
  );
}