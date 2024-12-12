import React from "react";
import "../assets/TicketDisplay.css";

function TicketDisplay({ tickets }) {
  return (
  <div>
    <div className="topic"> 
         <h1>Real-Time Ticketing System</h1>
    </div>
    <div className="ticket-display">
      <h2>Current Ticket Availability</h2>
      <p>Total Tickets: {tickets}</p>
    </div>
  </div>  
  );
}

export default TicketDisplay;

