CREATE TABLE categories(
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR NOT NULL
);

INSERT INTO categories (name) VALUES
                                  ('Spring Cleanup'),
                                  ('Summer Maintenance'),
                                  ('Autumn Cleanup'),
                                  ('Winter Protection'),
                                  ('Seasonal Garden Work'),
                                  ('Planting and Transplanting'),
                                  ('Pruning and Shaping'),
                                  ('Fertilizing and Plant Protection'),
                                  ('Lawn Mowing and Care'),
                                  ('Watering and Irrigation'),
                                  ('Garden Structures Construction and Maintenance'),
                                  ('Composting and Waste Management'),
                                  ('Pest Control'),
                                  ('Soil Preparation'),
                                  ('Garden Design and Landscaping');