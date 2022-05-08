import { Coffee } from '../App';
import React, { FormEvent } from 'react';
import {useLocation, useNavigate, useSearchParams} from 'react-router-dom';
import './AddCoffee.css';

interface CustomizedState {
    coffee: Coffee
}

const UpdateCoffee = () => {
    const state = useLocation().state as CustomizedState;
    const [searchParams] = useSearchParams();

    const getCoffee = (id: string) => {
        fetch('http://localhost:8080/coffee/'+id,
            {
                headers : { 
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                }
            }
        ).then(response => response.json())
        .then(data => {
            const coffee:Coffee = data.data
            state.coffee = coffee;
        });
    }
    if (state === null) {
        const id = searchParams.get("id") || "";
        getCoffee(id)
    }
    const coffee = state.coffee;
    const navigate = useNavigate();

    const sendCoffee = (e:FormEvent) => {
        e.preventDefault();
        const target = e.target as typeof e.target & {
            id: {value: string }
            name: { value: string };
            price: { value: string };
            weight: { value: string };
            roastlevel: { value: string };
        };
        fetch('/coffee', {
            method: 'put',
            body : JSON.stringify({
                id: target.id.value,
                name: target.name.value,
                price: target.price.value,
                weight: target.weight.value,
                roastlevel: target.roastlevel.value
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
                console.log(response.status, response);
        })
        .catch(function(error) {
            console.log(error);
        });
    }

    if (coffee === null) { return null; }
    return (
        <div className="App">
            <header className="App-header">Edit coffee</header>
            <form onSubmit={sendCoffee.bind(this)}>
                <label htmlFor="id">Id</label><br/><input id="id" type="text" name="id" value={coffee.id} readOnly></input><br/>
                <label htmlFor="name">Name</label><br/><input id="name" type="text" name="name" defaultValue={coffee.name}></input><br/>
                <label htmlFor="price">Price</label><br/><input id="price" type="text" name="price" defaultValue={coffee.price}></input><br/>
                <label htmlFor="weight">Weight</label><br/><input id="weight" type="text" name="weight" defaultValue={coffee.weight}></input><br/>
                <label htmlFor="roastlevel">Roast level</label><br/>
                <select id="roastlevel" name="roastlevel" defaultValue={coffee.roastlevel}>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                </select><br/>
                <input type="submit" value="Save" />
                <input type="submit" onClick={(e)=>{e.preventDefault();navigate("/");}} value="Back"/>
            </form>
        </div>
    );
}

export default UpdateCoffee;