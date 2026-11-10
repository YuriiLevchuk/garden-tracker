CREATE TABLE gardening_task_types(
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR NOT NULL,
    category_id INT REFERENCES categories(id)
);

INSERT INTO gardening_task_types (name, category_id) VALUES
                                                         -- Spring Cleanup (id = 1)
                                                         ('Rake winter debris from lawn', 1),
                                                         ('Remove dead foliage from perennials', 1),
                                                         ('Clean up garden beds', 1),
                                                         ('Uncover plants after winter', 1),
                                                         ('Edge flower beds', 1),

                                                         -- Summer Maintenance (id = 2)
                                                         ('Deadhead spent flowers', 2),
                                                         ('Water garden beds', 2),
                                                         ('Mulch planting beds', 2),
                                                         ('Weed flower beds', 2),
                                                         ('Support tall plants with stakes', 2),

                                                         -- Autumn Cleanup (id = 3)
                                                         ('Collect fallen leaves', 3),
                                                         ('Cut back perennials', 3),
                                                         ('Clean out gutters', 3),
                                                         ('Store garden tools for winter', 3),
                                                         ('Harvest vegetables', 3),

                                                         -- Winter Protection (id = 4)
                                                         ('Wrap trees with protective covers', 4),
                                                         ('Insulate sensitive plants', 4),
                                                         ('Spread winter mulch', 4),
                                                         ('Protect pots from frost', 4),
                                                         ('Shake snow off branches', 4),

                                                         -- Seasonal Garden Work (id = 5)
                                                         ('Change seasonal plant displays', 5),
                                                         ('Prepare garden for new season', 5),
                                                         ('Inspect garden after winter', 5),
                                                         ('Adjust irrigation schedule for season', 5),
                                                         ('Plan seasonal planting schedule', 5),

                                                         -- Planting and Transplanting (id = 6)
                                                         ('Plant flower bulbs', 6),
                                                         ('Plant annual flowers', 6),
                                                         ('Transplant seedlings outdoors', 6),
                                                         ('Plant trees and shrubs', 6),
                                                         ('Divide overgrown perennials', 6),

                                                         -- Pruning and Shaping (id = 7)
                                                         ('Prune roses', 7),
                                                         ('Trim hedges', 7),
                                                         ('Shape shrubs', 7),
                                                         ('Remove dead branches', 7),
                                                         ('Prune fruit trees', 7),

                                                         -- Fertilizing and Plant Protection (id = 8)
                                                         ('Apply spring fertilizer', 8),
                                                         ('Spread compost around plants', 8),
                                                         ('Apply fungicide treatment', 8),
                                                         ('Fertilize lawn', 8),
                                                         ('Check soil pH and adjust', 8),

                                                         -- Lawn Mowing and Care (id = 9)
                                                         ('Mow the lawn', 9),
                                                         ('Aerate the lawn', 9),
                                                         ('Overseed bare patches', 9),
                                                         ('Edge lawn borders', 9),
                                                         ('Scarify the lawn', 9),

                                                         -- Watering and Irrigation (id = 10)
                                                         ('Set up irrigation system', 10),
                                                         ('Water newly planted trees', 10),
                                                         ('Check drip lines for leaks', 10),
                                                         ('Adjust watering frequency', 10),
                                                         ('Winterize irrigation system', 10),

                                                         -- Garden Structures Construction and Maintenance (id = 11)
                                                         ('Build raised garden beds', 11),
                                                         ('Repair garden fence', 11),
                                                         ('Install garden trellis', 11),
                                                         ('Maintain garden shed', 11),
                                                         ('Build garden path', 11),

                                                         -- Composting and Waste Management (id = 12)
                                                         ('Turn compost pile', 12),
                                                         ('Add kitchen scraps to compost', 12),
                                                         ('Collect garden waste for composting', 12),
                                                         ('Harvest finished compost', 12),
                                                         ('Set up compost bin', 12),

                                                         -- Pest Control (id = 13)
                                                         ('Inspect plants for pests', 13),
                                                         ('Set up slug traps', 13),
                                                         ('Apply organic pest repellent', 13),
                                                         ('Introduce beneficial insects', 13),
                                                         ('Remove pest-infested plants', 13),

                                                         -- Soil Preparation (id = 14)
                                                         ('Dig over garden beds', 14),
                                                         ('Test soil quality', 14),
                                                         ('Mix in organic matter', 14),
                                                         ('Level and rake soil', 14),
                                                         ('Remove weeds before planting', 14),

                                                         -- Garden Design and Landscaping (id = 15)
                                                         ('Draw garden layout plan', 15),
                                                         ('Select plants for new border', 15),
                                                         ('Arrange decorative stones', 15),
                                                         ('Design patio planting scheme', 15),
                                                         ('Install garden lighting', 15);