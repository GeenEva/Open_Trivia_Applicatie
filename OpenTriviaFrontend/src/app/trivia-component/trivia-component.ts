import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TriviaService } from '../trivia-service/trivia-service';
import { TriviaQuestion, TriviaAnswer, AnswerResult } from '../trivia-model/trivia.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-trivia',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './trivia-component.html',
  styleUrls: ['./trivia-component.scss']
})
export class TriviaComponent {
  private router = inject(Router);
  private triviaService = inject(TriviaService);

  questions = signal<TriviaQuestion[]>([]);
  currentIndex = signal(0);

  result = signal<AnswerResult[] | null>(null);

  get score(): number {
    return this.result()?.filter(r => r.isWellAnswered).length ?? 0;
  }

  get total(): number {
    return this.result()?.length ?? 0;
  }

  constructor() {
    this.loadQuestions();
  }

  allQuestionsAnswered(): boolean {
    return this.questions().every(q => !!q.chosenAnswer);
  }

  resetQuiz() {
    this.questions.set([]);
    this.currentIndex.set(0);
    this.result.set(null);
    this.loadQuestions();
  }

  goHome() {
    this.resetQuiz();
    this.router.navigate(['/']);
  }

  loadQuestions() {
    this.triviaService.getQuestions().subscribe((questions) => {
      this.questions.set(
        questions.map(q => ({
          ...q,
          chosenAnswer: undefined
        }))
      );
    });
  }

  get currentQuestion() {
    return this.questions()[this.currentIndex()];
  }

  selectAnswer(questionId: string, answer: string) {
    this.questions.update(qs =>
      qs.map(q =>
        q.id === questionId
          ? { ...q, chosenAnswer: answer }
          : q
      )
    );
  }

  nextQuestion() {
    if (this.currentIndex() < this.questions().length - 1) {
      this.currentIndex.set(this.currentIndex() + 1);
    }
  }

  submitAll() {
    const answers: TriviaAnswer[] = this.questions().map(q => ({
      questionId: q.id,
      givenAnswer: q.chosenAnswer ?? ''
    }));

    this.triviaService.checkAnswers(answers).subscribe(res => {
      this.result.set(res);
    });
  }

}