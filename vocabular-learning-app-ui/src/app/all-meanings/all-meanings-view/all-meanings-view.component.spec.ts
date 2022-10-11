import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllMeaningsViewComponent } from './all-meanings-view.component';

describe('AllMeaningsViewComponent', () => {
  let component: AllMeaningsViewComponent;
  let fixture: ComponentFixture<AllMeaningsViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllMeaningsViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AllMeaningsViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
