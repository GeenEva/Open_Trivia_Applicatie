import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TriviaService } from './trivia-service';

describe('TriviaService', () => {
  let component: TriviaService;
  let fixture: ComponentFixture<TriviaService>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TriviaService],
    }).compileComponents();

    fixture = TestBed.createComponent(TriviaService);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
