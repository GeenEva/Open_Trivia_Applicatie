import { Routes } from '@angular/router';
import { StartScreenComponent } from './start-screen-component/start-screen-component';
import { TriviaComponent } from './trivia-component/trivia-component';
export const routes: Routes = [
  { path: '', component: StartScreenComponent },
  { path: 'quiz', component: TriviaComponent },
];