import React, { useState } from 'react';
import "../assets/ConfigurationForm.css";

function ConfigurationForm({ onSave }) {
  const [config, setConfig] = useState({
    totalTickets: 0,
    ticketsReleasedPerCycle: 0,
    ticketsRetrievedPerCycle: 0,
    maxTicketPool: 0,
  });

  const handleInputChange = (e) => {
    setConfig({
      ...config,
      [e.target.name]: parseInt(e.target.value) || 0,
    });
  };

  const handleSave = () => {
    onSave(config);
  };

  return (
 <div>
    <div className="header"> 
         <h1>Real-Time Ticketing System</h1>
    </div>
    <div className="form-container">
      <h2>Configuration</h2>
      <br/>
      <label>Total tickets
      <input
        type="number"
        name="totalTickets"
        placeholder="Total Tickets"
        onChange={handleInputChange}
      />
      </label>

      <label>Tickets Released Per Cycle
      <input
        type="number"
        name="ticketsReleasedPerCycle"
        placeholder="Tickets Released Per Cycle"
        onChange={handleInputChange}
      />
      </label>

      <label>Ticket Retrieved Per Cycle
      <input
        type="number"
        name="ticketsRetrievedPerCycle"
        placeholder="Tickets Retrieved Per Cycle"
        onChange={handleInputChange}
      />
      </label>

      <label> Maximum Ticket Pool Capacity
      <input
        type="number"
        name="maxTicketPool"
        placeholder="Maximum Ticket Pool Capacity"
        onChange={handleInputChange}
      />
      </label>
      
      <button onClick={handleSave}>Save Configuration</button>
    </div>
    </div>
  );
}

export default ConfigurationForm;
