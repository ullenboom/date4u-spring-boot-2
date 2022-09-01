package com.tutego.date4u.chatmessages;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

  final MessageRepository messages;

  final ElasticsearchOperations operations;

  public MessageService( MessageRepository messages, ElasticsearchOperations operations ) {
    this.messages = messages;
    this.operations = operations;
  }

  public void saveAll( List<Message> messages ) {
    this.messages.saveAll( messages );
  }

  public Streamable<Message> findAll() {
    return Streamable.of( messages.findAll() );
  }

  public List<Message> messagesBetween( long id1, long id2 ) {
    String id = Math.min( id1, id2 ) + "+" + Math.max( id1, id2 );
    return messages.findMessageByConversationIdOrderBySent( id );
  }

  public SearchHits<Message> search( String search ) {
    NativeSearchQuery query = new NativeSearchQueryBuilder()
        .withQuery( QueryBuilders.matchQuery( "message", search )
                        .fuzziness( Fuzziness.ONE ) )
        .build();
    SearchHits<Message> hits = operations.search( query, Message.class );
    return hits;
  }

}
