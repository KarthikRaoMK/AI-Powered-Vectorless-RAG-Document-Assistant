import Upload from "./components/Upload";
import Documents from "./components/Documents";
import AskAI from "./components/AskAi";

function App() {

    return (

        <div className="container mt-5">

            <h1 className="text-center">
                Spring AI RAG
            </h1>

            <hr />

            <Upload />

            <AskAI />

            <Documents />

        </div>

    );

}

export default App;