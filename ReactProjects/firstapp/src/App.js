import "./App.css";
import Counter from "./companents/Counter";
import Header from "./companents/Header";
import PersonInfo from "./companents/PersonInfo";
const persons={
  person1:{
    name:"Ebubekir Sıddık",
    age:20
  },
  person2:{
    name:"Muhammed Emir",
    age:21
  },
  person3:{
    name:"Selin",
    age:18
  },
  person4:{
    name:"Hasan",
    age:31
  }
}
function App() {
  return (
    <div>
      <Header/>
      <Counter/>
      <PersonInfo data={persons.person1} />
      <PersonInfo data={persons.person2} />
      <PersonInfo data={persons.person3} />
      <PersonInfo data={persons.person4} />
    </div>
  );
}

export default App;
