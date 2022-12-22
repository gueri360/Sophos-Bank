import { Component } from '@angular/core';

@Component({
  selector: 'app-show-client-products-component',
  templateUrl: './show-client-products-component.component.html',
  styleUrls: ['./show-client-products-component.component.scss']
})
export class ShowClientProductsComponentComponent {
  
  productList: any[];
  
  constructor(){
    this.productList = [
      {type: "CHECKING", accountNumber: 123345, balance: 1234, availableBalance: 1234, gmfExempt: "no"},
      {type: "CHECKING", accountNumber: 123345, balance: 1234, availableBalance: 1234, gmfExempt: "no"},
      {type: "CHECKING", accountNumber: 123345, balance: 1234, availableBalance: 1234, gmfExempt: "no"},
      {type: "CHECKING", accountNumber: 123345, balance: 1234, availableBalance: 1234, gmfExempt: "Yes"},
      {type: "CHECKING", accountNumber: 123345, balance: 1234, availableBalance: 1234, gmfExempt: "Yes"},
      {type: "CHECKING", accountNumber: 123345, balance: 1234, availableBalance: 1234, gmfExempt: "Yes"},
      {type: "CHECKING", accountNumber: 123345, balance: 1234, availableBalance: 1234, gmfExempt: "Yes"},
      {type: "CHECKING", accountNumber: 123345, balance: 1234, availableBalance: 1234, gmfExempt: "Yes"},
      {type: "CHECKING", accountNumber: 123345, balance: 1234, availableBalance: 1234, gmfExempt: "Yes"}
    ]
    }
}
