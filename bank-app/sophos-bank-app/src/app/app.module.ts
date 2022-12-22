import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { FooterComponent } from './components/footer/footer.component';
import { HomeComponent } from './components/home/home.component';
import { ListsComponent } from './components/common/lists/lists.component';
import { FormsComponent } from './components/common/forms/forms.component';
import { ModifyUserComponentComponent } from './components/users/modify-user-component/modify-user-component.component';
import { ShowAllClientsComponentComponent } from './components/users/show-all-clients-component/show-all-clients-component.component';
import { CreateTransactionComponent } from './components/transaction/create-transaction/create-transaction.component';
import { CreateProductComponent } from './components/product/create-product/create-product.component';
import { ModifyProductComponent } from './components/product/modify-product/modify-product.component';
import { ShowClientProductsComponentComponent } from './components/product/show-client-products-component/show-client-products-component.component';
import { ProductTransactionsComponent } from './components/product/product-transactions/product-transactions.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    FooterComponent,
    HomeComponent,
    ListsComponent,
    FormsComponent,
    ModifyUserComponentComponent,
    ShowAllClientsComponentComponent,
    CreateTransactionComponent,
    CreateProductComponent,
    ModifyProductComponent,
    ShowClientProductsComponentComponent,
    ProductTransactionsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
