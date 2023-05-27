CREATE OR REPLACE FUNCTION add_days_to_date(input_date DATE, num_days INTEGER)
    RETURNS DATE AS
$$
DECLARE
    result_date DATE;
BEGIN
    result_date := input_date + num_days;
    RETURN result_date;
END;
$$
    LANGUAGE plpgsql;