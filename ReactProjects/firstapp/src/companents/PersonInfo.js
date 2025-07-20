import React, {useState} from "react";

function PersonInfo({data : {name,age}}) {
  const [count, setcount] = useState(age);
  return (
    <div>
      <center>
        <h1>Infos</h1>
        <p>
          name: {name} <br />
          age: {count}
        </p>
        <button onClick={() => setcount(count + 1)}>Increase age</button>
      </center>
    </div>
  );
}

export default PersonInfo;
