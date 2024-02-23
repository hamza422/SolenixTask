class CombineEventData:
    def __init__(self, event, latitude, longitude):
        self.event = event
        self.latitude = latitude
        self.longitude = longitude

class Event:
    def __init__(self, occurrence_time, event_name,id,severity):
        self.occurrence_time = occurrence_time
        self.event_name = event_name
        self.id = id
        self.severity = severity

class Latitude:
    def __init__(self, timestamp, position):
        self.timestamp = timestamp
        self.position = position

class Longitude:
    def __init__(self, timestamp, position):
        self.timestamp = timestamp
        self.position = position
