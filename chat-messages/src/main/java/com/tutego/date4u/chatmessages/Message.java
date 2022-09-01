package com.tutego.date4u.chatmessages;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import java.time.OffsetDateTime;
import java.util.List;

@Document( indexName = "messages" )
public class Message {

  @Id
  public String id;

  @Field( type = FieldType.Keyword )
  public String conversationId;

  @Field( type = FieldType.Long )
  public List<Long> participantIds;

  @Field( type = FieldType.Text )
  public String message;

  @Field( type = FieldType.Date, format = DateFormat.date_time )
  public OffsetDateTime sent;

  public Message() { }

  public Message( String conversation, List<Long> participants,
                  String message, OffsetDateTime sent ) {
    this.conversationId = conversation;
    this.participantIds = participants;
    this.message = message;
    this.sent = sent;
  }

  @Override public String toString() {
    return String.format( "Message{id='%s', conversationId='%s'," +
                          "participantIds=%s, message='%s', sent=%s}",
                          id, conversationId, participantIds, message, sent );
  }
}