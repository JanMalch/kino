package io.github.janmalch.kino.problem;

import java.util.Map;

public interface ProblemTemplate {

  Problem generateProblem(Map<String, Object> fields, Object... detailParams);
}
