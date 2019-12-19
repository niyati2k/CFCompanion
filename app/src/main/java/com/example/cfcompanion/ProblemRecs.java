package com.example.cfcompanion;

import com.google.auto.value.AutoValue;

@AutoValue
abstract class ProblemRecs {
  abstract Problem problem();

  abstract int numberFriends();

  static Builder builder() {
    return new AutoValue_ProblemRecs.Builder();
  }

  @AutoValue.Builder
  abstract static class Builder {
    abstract Builder setProblem(Problem problem);

    abstract Builder setNumberFriends(int numberOfFriends);

    abstract ProblemRecs build();
  }
}
