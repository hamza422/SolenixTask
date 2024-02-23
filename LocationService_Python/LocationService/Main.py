import json
from datetime import datetime
from AllClasses import Event
from AllClasses import Latitude
from AllClasses import Longitude
from AllClasses import CombineEventData

class SpacecraftDataProcessor:
    def __init__(self, events_file, latitude_file, longitude_file):
        self.events = self.load_events(events_file)
        self.latitude_positions = self.load_Latitude_positions(latitude_file)
        self.longitude_positions = self.load_Longitude_positions(longitude_file)

    def load_events(self, events_file):
        with open(events_file, 'r') as f:
            events_data = json.load(f)
        listOfEvents = []
        for eventData in events_data:
            event = Event(datetime.strptime(eventData['occurrence_time'], "%Y-%m-%dT%H:%M:%S"), eventData['event_name'],eventData['id'], eventData['severity'])
            listOfEvents.append(event)
        return listOfEvents

    def load_Latitude_positions(self, positions_file):
        with open(positions_file, 'r') as f:
            positions_data = json.load(f)
        listOfLatitudes = []
        for position in positions_data:
            latitude = Latitude(datetime.strptime(position['timestamp'], "%Y-%m-%dT%H:%M:%S"), position['position'])
            listOfLatitudes.append(latitude)
        return listOfLatitudes

    def load_Longitude_positions(self, positions_file):
        with open(positions_file, 'r') as f:
            positions_data = json.load(f)
        listOfLongitude = []
        for position in positions_data:
            longitude = Longitude(datetime.strptime(position['timestamp'], "%Y-%m-%dT%H:%M:%S"), position['position'])
            listOfLongitude.append(longitude)
        return listOfLongitude

    def find_closest_position(self, positions, timestamp):
        closest_position = None
        min_difference = float('inf')
        for position in positions:
            timeComponent=position.timestamp.time()
            time_as_long = timeComponent.hour * 3600 + timeComponent.minute * 60 + timeComponent.second
            event_time_as_long = timestamp.time().hour * 3600 + timestamp.time().minute * 60 + timestamp.time().second
            difference = abs( time_as_long - event_time_as_long)
            if difference < min_difference:
                min_difference = difference
                closest_position = position
        return closest_position

    def combine_events_with_positions(self):
        listOfCombinedEvent=[]

        for event in self.events:
            event_time = event.occurrence_time
            closest_latitude = self.find_closest_position(self.latitude_positions, event_time)
            closest_longitude = self.find_closest_position(self.longitude_positions, event_time)
            combineEventData = CombineEventData(event, closest_latitude,closest_longitude)
            listOfCombinedEvent.append(combineEventData)

        return listOfCombinedEvent
    #
    def display_combined_data(self):
        listOfCombinedEvent=self.combine_events_with_positions()
        for combineEvent in listOfCombinedEvent:
            print(f"Event: {combineEvent.event.event_name}, Timestamp: {combineEvent.event.occurrence_time}, Latitude: {combineEvent.latitude.position}, Longitude: {combineEvent.longitude.position}")

processor = SpacecraftDataProcessor('..\events.json', '..\latitudes.json', '..\longitudes.json')
processor.display_combined_data()