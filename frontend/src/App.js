import './App.css';
import { ListMenu } from './components/ListMenu';

function App() {
  return (
    <div className="App">
      <header className="header">
        <span className="title">HashCode Judge System</span>
      </header>
      <main class="main">
        <aside class="left">
          <ListMenu />
        </aside>
        <result class="right">...</result>
      </main>
    </div>
  );
}

export default App;
