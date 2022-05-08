import React, { useState, useEffect } from 'react';
import { Link } from "react-router-dom";
import { AppProps, Coffee } from '../App';
import './CoffeeList.css';

const CoffeeList = (props:AppProps) => {
    const [data,setData] = useState<Coffee[]>([]);
    const getData=()=>{
        fetch('http://localhost:8080/coffees',
            {
                headers : { 
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                }
            }
        ).then(response => response.json())
        .then(data => {
            const coffeeArray:Coffee[] = data.data
            setData(coffeeArray);
        });
    };

    const removeCoffee=(id:string)=>{
        fetch('/coffee/'+id,
            {
                method: "delete",
                headers: { 
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                }
            }
        ).then(response => {
            if (response.status === 200) {
                getData()
            }
        });
    };

    useEffect(()=>{
        if (props.update) {
            getData()
            props.onChange(false);
        }
        getData()
    },[props])

    return (
        <div>
            <table>
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Weight</th>
                        <th>Roast level</th>
                    </tr>
                </thead>
                <tbody>
                {
                    data && data.length > 0 && data.map((item, i) => 
                        <tr key={i}>
                            <td><Link 
                                to={{pathname: "/edit", search: `?id=${item.id}`}} state={{coffee: item}}>{item.id}</Link></td>
                            <td>{item.name}</td>
                            <td>{item.price}</td>
                            <td>{item.weight}</td>
                            <td>{item.roastlevel}</td>
                            <td><input type="submit" value="Delete" onClick={() => removeCoffee(item.id)}></input></td>
                        </tr>
                    )
                }
                </tbody>
            </table>
        </div>
    )
}


export default CoffeeList;