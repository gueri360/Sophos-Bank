import { Component } from '@angular/core';

@Component({
  selector: 'app-modify-user-component',
  templateUrl: './modify-user-component.component.html',
  styleUrls: ['./modify-user-component.component.scss']
})
export class ModifyUserComponentComponent {
    texto: string

    constructor(){
      this.texto = "texto"
    }
}
