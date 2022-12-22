import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowAllClientsComponentComponent } from './show-all-clients-component.component';

describe('ShowAllClientsComponentComponent', () => {
  let component: ShowAllClientsComponentComponent;
  let fixture: ComponentFixture<ShowAllClientsComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShowAllClientsComponentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ShowAllClientsComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
