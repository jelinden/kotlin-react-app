import React, { useState } from 'react';
import CoffeeList from './app/CoffeeList';
import './App.css';
import AddCoffee from './app/AddCoffee';

export interface AppProps {
  update: boolean;
  coffee?: Coffee;
  onChange: (val: boolean) => void;
}

export interface Coffee {
  id: string;
  name: string;
  price: number;
  weight: number;
  roastlevel: number;
}

function App() {
  const [update=false, setUpdate] = useState<boolean>();
  
  function handleChange(newValue:boolean) {
    console.log("changing update to", newValue, "from", update);
    setUpdate(newValue);
  }

  return (
      <div className="App">
        <header className="App-header">
        Coffee list
        </header>
        <AddCoffee update={update} onChange={handleChange}></AddCoffee>
        <CoffeeList update={update} onChange={handleChange}></CoffeeList>
      </div>
  );
}

export default App;
