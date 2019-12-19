package com.example.cfcompanion;

import java.util.HashSet;
import java.util.Set;

class User {
  private String handle;
  private Set<Problem> solvedProblems = new HashSet<>();
  private Set<Problem> attemptedProblems = new HashSet<>();

  public Set<Problem> getSolvedProblems() {
    return solvedProblems;
  }

  public Set<Problem> getAttemptedProblems() {
    return attemptedProblems;
  }

  void setHandle(String username) {
    this.handle = username;
  }

  String getHandle() {
    return this.handle;
  }

  void addSolvedProblem(Problem problem) {
    solvedProblems.add(problem);
  }

  void addAttemptedProblem(Problem problem) {
    attemptedProblems.add(problem);
  }
}
