-- Fix this:
INSERT INTO crew_member (
  id, first_name, last_name, email, phone_number, role, password
)
VALUES 
  (50, 'Alice', 'Smith', 'alice@tcu.edu', '123-456-7890', 'Producer', 'hunter2'),
  (51, 'Bob', 'Jones', 'bob@tcu.edu', '555-121-2121', 'Director', 'hunter2'),
  (52, 'Tucker', 'Rinaldo', 'tucker@example.com', '555-1234', 'CREW', '$2a$10$Dow1bApUbQnSSiZmhR.C1OlGgGj3xgV1swnL0TJqf5Q4ac/jPRQ0C');
