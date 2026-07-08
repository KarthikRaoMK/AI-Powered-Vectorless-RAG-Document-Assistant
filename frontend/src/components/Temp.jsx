import { useState, useEffect, useRef } from "react";
import ChatBubble from "./ChatBubble";

function AskAI() {

    const [question, setQuestion] = useState("");

    const [messages, setMessages] = useState([
        {
            sender: "AI",
            text: "Hello! Ask me anything."
        }
    ]);

    const [loading, setLoading] = useState(false);

    // Reference to the bottom of the chat
    const chatEndRef = useRef(null);

    // Automatically scroll to the newest message
    useEffect(() => {

        chatEndRef.current?.scrollIntoView({
            behavior: "smooth"
        });

    }, [messages]);

    const askQuestion = async () => {

        if (question.trim() === "") return;

        const currentQuestion = question;

        // Display user's message immediately
        setMessages(prev => [
            ...prev,
            {
                sender: "You",
                text: currentQuestion
            }
        ]);

        setQuestion("");
        setLoading(true);

        try {

           const response = await fetch(
    `${import.meta.env.VITE_API_URL}/rag/ask?question=${encodeURIComponent(currentQuestion)}`);

              const data = await response.json();

            setMessages(prev => [
                ...prev,
                {
                    sender: "AI",
                    text: data.answer,
                    sources: data.sources
                }
            ]);

        } catch (error) {

            setMessages(prev => [
                ...prev,
                {
                    sender: "AI",
                    text: "Error contacting server."
                }
            ]);

        }

        setLoading(false);

    };

    return (

        <div className="card mt-4 shadow">

            <div className="card-body">

                <h3 className="mb-4">
                     Ask AI Assistant
                </h3>

                {/* Chat Area */}

                <div
                    style={{
                        height: "450px",
                        overflowY: "auto",
                        background: "#f5f5f5",
                        borderRadius: "15px",
                        padding: "20px",
                        marginBottom: "20px"
                    }}
                >

                    {messages.map((msg, index) => (

                        <ChatBubble
                            key={index}
                            sender={msg.sender}
                            text={msg.text}
                            sources={msg.sources || []}
                        />

                    ))}

                    {loading && (

                        <div
                            style={{
                                display: "flex",
                                justifyContent: "flex-start",
                                marginBottom: "15px"
                            }}
                        >

                            <div
                                style={{
                                    background: "white",
                                    padding: "15px",
                                    borderRadius: "15px",
                                    boxShadow: "0 2px 8px rgba(0,0,0,0.1)"
                                }}
                            >

                                <div
                                    className="spinner-border spinner-border-sm text-success"
                                    role="status"
                                ></div>

                                <span className="ms-2">
                                    Gemini is thinking...
                                </span>

                            </div>

                        </div>

                    )}

                    {/* Auto-scroll target */}
                    <div ref={chatEndRef}></div>

                </div>

                {/* Input */}

                <div className="input-group">

                    <input
                        type="text"
                        className="form-control"
                        placeholder="Ask anything..."
                        value={question}
                        onChange={(e) => setQuestion(e.target.value)}
                        onKeyDown={(e) => {

                            if (e.key === "Enter") {

                                askQuestion();

                            }

                        }}
                    />

                    <button
                        className="btn btn-success"
                        onClick={askQuestion}
                    >
                        Send
                    </button>

                </div>

            </div>

        </div>

    );

}

export default AskAI;