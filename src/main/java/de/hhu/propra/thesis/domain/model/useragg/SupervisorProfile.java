package de.hhu.propra.thesis.domain.model.useragg;

import de.hhu.propra.thesis.domain.model.shared.Topic;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public final class SupervisorProfile extends User {
  @MappedCollection(idColumn = "user_id")
  private final Set<Topic> topics = new HashSet<>();

  public SupervisorProfile(GithubIdentity identity, String email) {
    super(identity, Role.SUPERVISOR, email);
  }

  public Set<Topic> getTopics() {
    return Collections.unmodifiableSet(topics);
  }


  public void addTopic(Topic topic) {
    topics.add(Objects.requireNonNull(topic, "topic must not be null"));
  }


  public void replaceTopics(Set<Topic> newTopics) {
    Objects.requireNonNull(newTopics, "topics must not be null");
    topics.clear();
    for (Topic t : newTopics) {
      topics.add(Objects.requireNonNull(t, "topic must not be null"));
    }
  }


}
