import { AppProps } from '../App';
import React, { FormEvent } from 'react';
import './AddCoffee.css';

const AddCoffee = (props:AppProps) => {

    const sendCoffee = (e:FormEvent) => {
        e.preventDefault();
        const target = e.target as typeof e.target & {
            name: { value: string };
            price: { value: string };
            weight: { value: string };
            roastlevel: { value: string };
        };
        fetch('/coffee', {
            method: 'post',
            body : JSON.stringify({
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
                props.onChange(true);
        })
        .catch(function(error) {
            console.log(error);
        });
    }

    return (
        <div>
            <form onSubmit={sendCoffee.bind(this)}>
                <label htmlFor="name">Name</label><br/><input id="name" type="text" name="name"></input><br/>
                <label htmlFor="price">Price</label><br/><input id="price" type="text" name="price"></input><br/>
                <label htmlFor="weight">Weight</label><br/><input id="weight" type="text" name="weight"></input><br/>
                <label htmlFor="roastlevel">Roast level</label><br/>
                <select id="roastlevel" name="roastlevel">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                </select><br/>
                <input type="submit" value="Save" />
            </form>
        </div>
    );
}


export default AddCoffee;