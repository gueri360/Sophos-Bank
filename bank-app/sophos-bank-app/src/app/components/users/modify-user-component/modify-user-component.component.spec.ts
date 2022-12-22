import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModifyUserComponentComponent } from './modify-user-component.component';

describe('ModifyUserComponentComponent', () => {
  let component: ModifyUserComponentComponent;
  let fixture: ComponentFixture<ModifyUserComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModifyUserComponentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModifyUserComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
