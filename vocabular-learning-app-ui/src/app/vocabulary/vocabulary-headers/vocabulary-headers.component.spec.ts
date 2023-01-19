import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VocabularyHeadersComponent } from './vocabulary-headers.component';

describe('VocabularyHeadersComponent', () => {
  let component: VocabularyHeadersComponent;
  let fixture: ComponentFixture<VocabularyHeadersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VocabularyHeadersComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VocabularyHeadersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
