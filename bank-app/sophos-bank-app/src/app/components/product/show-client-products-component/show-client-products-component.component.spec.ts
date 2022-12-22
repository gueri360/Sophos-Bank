import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowClientProductsComponentComponent } from './show-client-products-component.component';

describe('ShowClientProductsComponentComponent', () => {
  let component: ShowClientProductsComponentComponent;
  let fixture: ComponentFixture<ShowClientProductsComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShowClientProductsComponentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ShowClientProductsComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
