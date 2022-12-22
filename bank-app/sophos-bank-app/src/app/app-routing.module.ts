import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { CreateProductComponent } from './components/product/create-product/create-product.component';
import { ModifyProductComponent } from './components/product/modify-product/modify-product.component';
import { ProductTransactionsComponent } from './components/product/product-transactions/product-transactions.component';
import { ShowClientProductsComponentComponent } from './components/product/show-client-products-component/show-client-products-component.component';
import { CreateTransactionComponent } from './components/transaction/create-transaction/create-transaction.component';
import { CreateUserComponentComponent } from './components/users/create-user-component/create-user-component.component';
import { ModifyUserComponentComponent } from './components/users/modify-user-component/modify-user-component.component';
import { ShowAllClientsComponentComponent } from './components/users/show-all-clients-component/show-all-clients-component.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'client/create', component: CreateUserComponentComponent },
  { path: 'client/modify', component: ModifyUserComponentComponent },
  { path: 'client/show', component: ShowAllClientsComponentComponent },
  { path: 'product/create', component: CreateProductComponent},
  { path: 'product/modify', component: ModifyProductComponent},
  { path: 'product/show', component: ShowClientProductsComponentComponent},
  { path: 'product/specific', component: ProductTransactionsComponent },
  { path: 'transaction/create', component: CreateTransactionComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
