package com.example.cfcompanion;

import java.util.List;
import java.util.Objects;

class Problem {

  private Integer contestId;

  private String index;

  private String name;

  private Integer rating;

  private List<String> tags;

  public Problem(Integer contestId, String index, String name, Integer rating, List<String> tags) {
    this.contestId = contestId;
    this.index = index;
    this.name = name;
    this.rating = rating;
    this.tags = tags;
  }

  public Integer getContestId() {
    return contestId;
  }

  public void setContestId(Integer contestId) {
    this.contestId = contestId;
  }

  public String getIndex() {
    return index;
  }

  public void setIndex(String index) {
    this.index = index;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getRating() {
    return rating;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Problem problem = (Problem) o;
    return contestId.equals(problem.contestId) && index.equals(problem.index);
  }

  @Override
  public int hashCode() {
    return Objects.hash(contestId, index);
  }

  @Override
  public String toString() {
    return "Problem{"
        + "contestId="
        + contestId
        + ", index='"
        + index
        + '\''
        + ", name='"
        + name
        + '\''
        + ", rating="
        + rating
        + ", tags="
        + tags
        + '}';
  }
}
