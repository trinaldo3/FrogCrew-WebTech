-- Fix this:
INSERT INTO crew_member (
  id, first_name, last_name, email, phone_number, role, password
)
VALUES 
  (50, 'Alice', 'Smith', 'alice@tcu.edu', '123-456-7890', 'Producer', 'hunter2'),
  (51, 'Bob', 'Jones', 'bob@tcu.edu', '555-121-2121', 'Director', 'hunter2'),
  (52, 'Tucker', 'Rinaldo', 'tucker@example.com', '555-1234', 'CREW', '$2a$10$Dow1bApUbQnSSiZmhR.C1OlGgGj3xgV1swnL0TJqf5Q4ac/jPRQ0C');


INSERT INTO game (schedule_id, game_date, call_time, location, opponent, is_finalized, sport)
VALUES 
  (1, '2025-05-05', '17:00:00', 'Amon G. Carter Stadium', 'SMU', false, 'Lacrosse'),
  (1, '2025-05-12', '18:30:00', 'Schollmaier Arena', 'Baylor', true, 'Basketball'),
  (2, '2025-05-20', '19:00:00', 'Lupton Stadium', 'Texas Tech', false, 'Football');
