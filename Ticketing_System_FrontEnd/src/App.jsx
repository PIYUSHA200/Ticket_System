import React, { useState } from "react";
import ConfigurationForm from "./components/ConfigurationForm";
import TicketDisplay from "./components/TicketDisplay";
import ControlPanel from "./components/ControlPanel";
import LogDisplay from "./components/LogDisplay";
import "./App.css";

function App() {
  const [tickets, setTickets] = useState(null);
  const [logs, setLogs] = useState([]);
  const [isConfigured, setIsConfigured] = useState(false); // Track configuration state
  const [config, setConfig] = useState({}); // Store configuration settings
  const [timer, setTimer] = useState(null); 
  const [ticketIdCounter, setTicketIdCounter] = useState(1); // Track unique ticket IDs

  const handleSaveConfig = (config) => {
    if (Object.values(config).some((value) => isNaN(value) || value < 0)) {
      alert("Invalid configuration: all values must be non-negative numbers.");
      return;
    }
    setTickets(Math.max(0, config.totalTickets)); // Ensure total tickets is non-negative
    setConfig(config); // Save the configuration object
    setLogs((prevLogs) => [...prevLogs, "Configuration saved."]);
    setIsConfigured(true); // After config is saved, switch to the main page
  };

  const handleStartSystem = () => {
    setLogs((prevLogs) => [...prevLogs, "System started."]);

    const newTimer = setInterval(() => {
      setTickets((prevTickets) => {
        let newTicketCount = prevTickets;
        let newTicketIdCounter = ticketIdCounter;

        // Release tickets if possible
        if (newTicketCount < config.maxTicketPool) {
          const ticketsToRelease = Math.min(config.ticketsReleasedPerCycle, config.maxTicketPool - newTicketCount);
          newTicketCount += ticketsToRelease;
          setLogs((prevLogs) => [
            ...prevLogs,
            `Vendor added ${ticketsToRelease} tickets. Total tickets: ${newTicketCount}`,
          ]);
        }

        // Purchase tickets if possible
        if (newTicketCount > 0 && config.ticketsRetrievedPerCycle > 0) {
          const ticketsToPurchase = Math.min(config.ticketsRetrievedPerCycle, newTicketCount);
          const purchasedTicketIds = Array.from(
            { length: ticketsToPurchase },
            (_, i) => newTicketIdCounter + i
          );
          newTicketIdCounter += ticketsToPurchase;
          newTicketCount -= ticketsToPurchase;
          setLogs((prevLogs) => [
            ...prevLogs,
            `Customer purchased ${ticketsToPurchase} tickets. Remaining tickets: ${newTicketCount}`,
          ]);
        }

        setTicketIdCounter(newTicketIdCounter); 
        return Math.max(0, newTicketCount); 
      });
    }, 1000); // Adjust interval time (1000 ms = 1 second)

    setTimer(newTimer); // Store the interval ID
  };

  const handleStopSystem = () => {
    setLogs((prevLogs) => [...prevLogs, "System stopped."]);
    clearInterval(timer); // Clear the interval when the system stops
    setTimer(null); // Reset timer state
  };

  const handleReset = () => {
    setTickets(null);
    setLogs([]);
    setConfig({});
    setIsConfigured(false); 
    setTicketIdCounter(1); 
  };

  return (
    <div className="app">
      {!isConfigured ? (
        // Show configuration form when not configured
        <ConfigurationForm onSave={handleSaveConfig} />
      ) : (
        
        <div>
          <TicketDisplay tickets={tickets} />
          <ControlPanel
            onStart={handleStartSystem}
            onStop={handleStopSystem}
            onReset={handleReset} 
          />
          <LogDisplay logs={logs} />
        </div>
      )}
    </div>
  );
}

export default App;
