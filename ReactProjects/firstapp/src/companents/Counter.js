import React,{useState} from "react";

function Counter() {
    const [count,setcount]=useState(0);
  return (
    <div>
      <center>
        <h1>{count}</h1>
        <button onClick={() => setcount(count+1)}>Arttır</button>
      </center>
    </div>
  );
}

export default Counter;
