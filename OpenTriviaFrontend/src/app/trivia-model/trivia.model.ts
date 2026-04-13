export interface TriviaQuestion {
  id: string;
  question: string;
  chosenAnswer?: string;
  allAnswers: string[];
}

//antwoorden die gegeven worden
export interface TriviaAnswer {
  questionId: string;
  givenAnswer: string;
}

//de in de backend gecheckte antwoorden
export interface AnswerResult {
  questionId: string;
  question: string;
  isWellAnswered: boolean;
  correctAnswer: string;
}



