import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllMeaningsHomeComponent } from './all-meanings-home.component';

describe('AllMeaningsHomeComponent', () => {
  let component: AllMeaningsHomeComponent;
  let fixture: ComponentFixture<AllMeaningsHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllMeaningsHomeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AllMeaningsHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
