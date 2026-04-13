import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TriviaQuestion, TriviaAnswer, AnswerResult } from '../trivia-model/trivia.model';


@Injectable({
  providedIn: 'root',
})
export class TriviaService {

  private apiUrl = 'http://localhost:8080/api/opentrivia/questions'

  constructor(private http: HttpClient) { }

  getQuestions(): Observable<TriviaQuestion[]> {
    return this.http.get<TriviaQuestion[]>(this.apiUrl);
  }

  checkAnswers(answers: TriviaAnswer[]): Observable<AnswerResult[]> {
  return this.http.post<AnswerResult[]>(
    'http://localhost:8080/api/opentrivia/checkAnswers',
      answers
  );
}

}