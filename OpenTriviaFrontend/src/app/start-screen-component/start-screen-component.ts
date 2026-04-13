import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-start-screen-component',
  imports: [],
  standalone: true,
  templateUrl: './start-screen-component.html',
  styleUrl: './start-screen-component.scss',
})
export class StartScreenComponent {
 constructor(private router: Router) {}

  startQuiz() {
    this.router.navigate(['/quiz']);
  } 
  
}