import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllMeaningsListComponent } from './all-meanings-list.component';

describe('AllMeaningsListComponent', () => {
  let component: AllMeaningsListComponent;
  let fixture: ComponentFixture<AllMeaningsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllMeaningsListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AllMeaningsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
