import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgGridSampleComponent } from './ag-grid-sample.component';

describe('AgGridSampleComponent', () => {
  let component: AgGridSampleComponent;
  let fixture: ComponentFixture<AgGridSampleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AgGridSampleComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AgGridSampleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
