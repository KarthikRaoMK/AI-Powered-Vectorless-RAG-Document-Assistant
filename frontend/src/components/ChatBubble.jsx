function ChatBubble({ sender, text, sources = [] }) {

    const isUser = sender === "You";

    return (

        <div
            style={{
                display: "flex",
                justifyContent: isUser ? "flex-end" : "flex-start",
                marginBottom: "15px"
            }}
        >

            <div
                style={{
                    background: isUser ? "#0d6efd" : "#ffffff",
                    color: isUser ? "white" : "black",
                    padding: "12px 16px",
                    borderRadius: "18px",
                    maxWidth: "75%",
                    boxShadow: "0 2px 8px rgba(0,0,0,0.1)"
                }}
            >

                <strong>{sender}</strong>

                <br />

                <div style={{ whiteSpace: "pre-wrap" }}>
                    {text}
                </div>

                {!isUser && sources.length > 0 && (

                    <div
                        style={{
                            marginTop: "15px",
                            paddingTop: "10px",
                            borderTop: "1px solid #ddd"
                        }}
                    >

                        <strong>Sources</strong>

                        <ul
                            style={{
                                marginTop: "8px",
                                marginBottom: 0
                            }}
                        >

                            {sources.map((source, index) => (

                                <li key={index}>

                                    <strong>{source.title}</strong>

                                    {" "} (Chunk {source.chunkNumber})

                                </li>

                            ))}

                        </ul>

                    </div>

                )}

            </div>

        </div>

    );

}

export default ChatBubble;